from fastapi import FastAPI, HTTPException, status, Query
from typing import Annotated
from pydantic import BaseModel

from mistralai.client import Mistral
import chromadb
from chromadb import Documents, EmbeddingFunction, Embeddings
from chromadb.utils.embedding_functions import register_embedding_function

import os

MISTRAL_API_KEY = os.getenv("MISTRAL_API_KEY")

app = FastAPI()

@register_embedding_function
class MistralEmbeddingFunction(EmbeddingFunction):

    def __init__(self):
        self.model = Mistral(api_key=MISTRAL_API_KEY) 

    def __call__(self, input: Documents) -> Embeddings:
        res = self.model.embeddings.create(model="mistral-embed", inputs=input)
        return [x.embedding for x in res.data] 

    @staticmethod
    def name() -> str:
        return "Mistral EF (API)"

# Create Collection
chroma_client = chromadb.PersistentClient(path="./comic_embeddings_db")
collection = chroma_client.get_or_create_collection(
    name = "comic_embeddings",
    embedding_function = MistralEmbeddingFunction()
)

class Recommendation(BaseModel):
    comic_id : int
    distance : float

# increase diversity of comics?
@app.get("/recommendations", 
        status_code = status.HTTP_200_OK, 
        description="returns similar comic ids sorted by relevance. Input comics must be unique. Invalid comic ids are ignored.")
def recommend_comics(comic_ids : Annotated[list[str], Query()]):
    n_results = 5
    query_results = collection.get(ids=comic_ids, include=["embeddings"])
    
    valid_ids = query_results["ids"]
    valid_ids_set = set(valid_ids)

    recommendations : dict[int, float] = {}

    for i, valid_id in enumerate(valid_ids): 
        query_results2 = collection.query(query_embeddings=query_results["embeddings"][i], n_results=n_results) # k * query cost -> Bottle neck
        for id_, distance_ in zip(query_results2["ids"][0], query_results2["distances"][0]): 
            if(id_ in recommendations) : recommendations[id_] = min(recommendations[id_], distance_)
            else : recommendations[id_] = distance_

    result : list[Recommendation] = []
    for id_ in recommendations: 
        if(id_ in valid_ids_set): continue 
        r : Recommendation = Recommendation(comic_id = id_, distance= recommendations[id_])
        result.append(r)
    
    result.sort(key = lambda r : r.distance) 
    valid_ids = [int(x) for x in valid_ids]
    return {
        "valid_comic_ids" : valid_ids, 
        "recommendations" : result
    }


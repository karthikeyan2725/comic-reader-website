package com.karthikeyan2527.comic_reader_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadingHistoryDTO {

    UserDTO user;

    List<HistoryItemDTO> history;
}
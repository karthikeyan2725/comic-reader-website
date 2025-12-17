function computePublishedAgoString(time){
    var days = Math.floor((Date.now() - new Date(time)) / (60 * 60 * 24 * 365))
    if(days == 0) return "Today"
    if(days == 1) return days + " day ago"
    return days + " days ago"
}

export default computePublishedAgoString
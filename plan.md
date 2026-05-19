# Centroid Finder Video Plan

# Main idea:
- Grab frame from video using VideoExperimentApp
- save frame to image
- grab file pathname of image 
- pass file path name to imageSummaryApp
- parse String filepath name to command line arguments
- run ImageSummaryApp to get binarzied image
- save binarized image and groups csv
- Validate the salmander moved by looping process again
- compare first location to last location or whatever we set as the intervals to check 


# Diagram
VideoExperimentApp -> ImageSummaryApp -> Get image and groups number -> compare locations 

# Flow
- Feed sample salamander vide into video proccesor
- Video processor grabs first frame of video
- Distance Image binarizer uses Euclidean color distance on to build a 2d grid out of our image populated with 1s and 0s based on the euclidean color distance between target color and pixel color
- find connected groups finds the connected groups of 1s (our salamander and and the avgX and avgY coordinates of the salamander(the centroid))
- grab timestamp of created group and centroid and log it to csv(maybe using Printwriter function in ImageSummaryApp)
- loop for every frame in the video
# Centroid Finder Video Plan

# Main idea:
- Grab frame from video using VideoExperimentApp
- save frame to image
- grab frame of image 
- pass frame to imageProcessor
- run ImageProccesor to get binarzied image
- save binarized image and groups csv
- Validate the salmander moved by looping process again
- compare first location to last location or whatever we set as the intervals to check 



# Diagram
VideoProcessor -> ImageProcessor -> VideoApp 

# Flow
- Feed sample salamander vide into video proccesor
- Video processor grabs first frame of video
- Image processor receives image
- Distance Image binarizer uses Euclidean color distance on to build a 2d grid out of our image populated with 1s and 0s based on the euclidean color distance between target color and pixel color
- find connected groups finds the connected groups of 1s (our salamander and and the avgX and avgY coordinates of the salamander(the centroid))
- grab timestamp of created group and centroid and log it to csv(maybe using Printwriter function in ImageProcessor)
- loop for every frame in the video
- print csv and png files

# Tracking Validation
- We track the salamander by displaying the timestamp and centroid of the salamander frame by frame as the video runs.

# Target Color & Threshold
- We chose a good target color and threshold by trial and error.
- Cycled colors and thresholds till we were able to output a clear picture of the mander
- Target Color: 115938
- Threshold: 115
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
## Implementation 

1. Create Express server project.
- Add server and processro folders
- move files into their correct folders
- run npm init in the server folder
2. Install express, dotenv, and uuid.
- install what we need for project
- also install nodemon for dev
- update scripts

3. Create .env file with paths.
- this will have port number and any other hidden variables needed
4. Create videos, results, and thumbnails folders.
- add these the server folder
5. Put videoprocessor.jar in the server project.
- have jar file in the root directory and test run
6. Set up express.static for /videos and /results.
- build enpoints
7. Build GET /api/videos.
- this will return all videos
8. Build GET /thumbnail/:filename.
- this will return the thunmbnail with file name
9. Build POST /process/:filename.
- used to send video to process
10. Validate targetColor and threshold.
- this needs to run with are jar or command line arguements
11. Generate jobId with UUID.
- unique id for the job
12. Build input video path.
- uses the video processor
13. Build output CSV path.
- writes the centroid or mander to the csv file with timestamp and coords
14. Store job status as processing.
- stores to processing so the user knows the app and video is working
15. Use child_process.spawn to run the JAR.
- runs through jar
16. Make sure the request returns 202 immediately.
- saying hey we are all good and working
17. Track when the job is done or check if the CSV exists.
- lets user know job is done and confirms the csv was created
18. Build GET /process/:jobId/status.
- tracks job status
19. Test every endpoint.
- either use postman or insomia
20. Clean up README/serverplan notes.
- this is a rough rough draft plan on what needs to be done, will updated when we work down the check list and add more as needed
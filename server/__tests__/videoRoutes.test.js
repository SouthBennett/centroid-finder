import request from "supertest"; // import Supertest to simualte HTTP requests
import express from "express"; // import express to create a small test server
import videoRoutes from "../routes/videoRoutes.js"; //import the real video routes from the project

const app = express(); // creates instance of express 

app.use(express.json());// allow json request bodies

app.use("/api", videoRoutes); // mount routes under /api

// Test the GET /api/videos endpoint
test("GET /api/videos returns a list of videos", async () => {
  // Send a fake GET request to the endpoint
  const response = await request(app).get("/api/videos");
  //Check that the endpoint returns a status 200
  expect(response.statusCode).toBe(200);
  // Check that the response body is an array
  expect(Array.isArray(response.body)).toBe(true);
});

// Test invalid thumbnail request
test("GET / api/thumbnail/:filename returns 404 for missing video", async () => {
  // send request using fake filename
  const response = await request(app).get("/api/thumbnail/fakevideo.mp4");
  // verify endpoint returns 404 not found
  expect(response.statusCode).toBe(404);
  // verify response contains error message
  expect(response.body.error).toBe("Video not found"); 
});

// Test successful thumbnail generation
test("GET /api/thumbnail/:filename returns a thumbnail image", async () => {
  // Send request using a real video filename
  const response = await request(app).get("/api/thumbnail/ensantina.mp4");
  // verify endpoint returns success 200 status
  expect(response.statusCode).toBe(200);
  // verify response is a JPEG image
  expect(response.header["content-type"]).toContain("image/jpeg");
});

// Test process endpoint validation
test("POST /api/process/:filename returns 400 when query params are missing", async () => {
  // Send request without target color or threshold
  const response = await request(app).post("/api/process/ensantina.mp4");
  // verify endpoint rejects imcomplete requests
  expect(response.statusCode).toBe(400);
  // verify correct error message is returned
  expect(response.body.error).toBe("Missing target color and a threshold to find the mander")
});

// Test successful processing job creation 
test("POST /api/process/:filename creates a processing job", async () => {
  // Send request with valid query parameters
  const response = await request(app)
    .post("/api/process/ensantina.mp4")
    .query({
      targetColor: 115938,
      threshold: 115
    });
  // verify endpoint accepts the processing request
  expect(response.statusCode).toBe(202);
  // verify a jobID was returned
  // toBeDefined() checks if the api returned SOME kind of jobID.
  // toBeDefined() doesnt care what the value is, type, or exact string. 
  // toBeDefined() only checks that what we got back is not undefined
  expect(response.body.jobID).toBeDefined();
});

// Test invalid job status requests
test("GET /api/process/:jobID/status returns 404 for invalid job IDs", async () => {
  // Send request using a fake job ID
  const response = await request(app)
    .get("/api/process/fake-job-id/status");
  
  // verify endpoint returns not found
  expect(response.statusCode).toBe(404);
  // verify correct error message is returned
  expect(response.body.error).toBe("You have no Job!!");
});

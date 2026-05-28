import request from "supertest"; // import Supertest to simualte HTTP requests
import express from "express"; // import express to create a small test server
import videoRoutes from "../routes/videoRoutes.js"; //import the real video routes from the project

const app = express(); // creates instance of express 

app.use(express.json()) // allow json request bodies

app.use("/api", videoRoutes); // mount routes under /api

// Test the GET /api/videos endpoint
test("GET /api/videos returns a list of videos", async () => {
  // Send a fake GET request to the endpoint
  const response = await request(app).get("/api/videos");
  //Check that the endpoint returns a status 200
  expect(response.statusCode).toBe(200);
  // Check that the response body is an array
  expect(Array.isArray(response.body)).toBe(true);
})

// Test invalid thumbnail request
test("GET / api/thumbnail/:filename returns 404 for missing video", async () => {
  // send request using fake filename
  const response = await request(app).get("/api/thumbnail/fakevideo.mp4");
  // verify endpoint returns 404 not found
  expect(response.statusCode).toBe(404);
  // verify response contains error message
  expect(response.body.error).toBe("Video not found"); 
})
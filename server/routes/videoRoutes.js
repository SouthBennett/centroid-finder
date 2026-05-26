import express from "express";

const router = express.Router();

router.get("/videos", (req, res) => {
  res.send("Video route works!")
});

export default router;
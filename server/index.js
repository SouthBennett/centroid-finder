import express from "express";
import dotenv from "dotenv";
import chalk from "chalk";
import videoRoutes from "./routes/videoRoutes.js";
import cors from "cors";



dotenv.config({ path: "../.env" });

const app = express();
app.use(cors());

const PORT = process.env.PORT;

app.use(express.json());

app.use("/api", videoRoutes);

app.use("/videos", express.static(process.env.VIDEO_DIR));

app.use("/results", express.static(process.env.RESULTS_DIR));

app.get("/", (req, res) => {
    res.send("Lets find the Mander!!");
});

app.listen(PORT, () => {
    console.log(chalk.blue(`-----------------****------------  Mander Server running on port http://localhost:${PORT}`));
} )
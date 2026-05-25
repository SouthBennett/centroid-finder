import express from "express";
import dotenv from "dotenv";
import chalk from "chalk"

dotenv.config({ path: "../.env" });

const app = express();

const PORT = process.env.PORT;

app.use(express.json());

app.use("/videos", express.static(process.env.VIDEO_DIR));

app.use("/results", express.static(process.env.RESULTS_DIR));

app.get("/", (req, res) => {
    res.send("Lets find the Mander!!");
});

app.listen(PORT, () => {
    console.log(chalk.blue(`-----------------****------------  Mander Server running on port http://localhost:${PORT}`));
} )
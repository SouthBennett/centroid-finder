// Utility functions for generating file paths
export function getVideoPath(filename) {
    return `./videos/${filename}`;
}

// Utility function to generate the path for the result CSV file based on the video filename
export function getResultPath(filename) {
    return `./results/${filename}.csv`;
}

// Utility function to generate the path for the thumbnail image based on the video filename
export function getThumbnailPath(filename) {
    return `./thumbnails/${filename}.jpg`;
}
# Goals

# Easy

- Download CSV button

We are already writing CSV files, so this would be easy. We would just need to add a button on the frontend that lets the user download the generated CSV.

- Compare original image vs processed image side-by-side

We already have the original image and the processed image, so we would just need to display both of them next to each other in React.

- Save observations/notes for each video

This would be fairly simple. We could add a text box where users can write notes about a video and save them to a file or database.

- Furthest point reached from starting position

Since we already have centroid coordinates, we can compare every centroid to the starting position and find the furthest distance reached.


# Goal

- Log the total distance the salamander traveled

We already calculate centroids, so we can add up the distance between each centroid position to find the total distance traveled.

- Track when it's moving and when it's not moving and compare the two time differences

By comparing centroid positions between frames, we can determine if the salamander is moving or resting and calculate how much time it spends doing each.

- Track how fast the salamander is moving over time

Once we know the distance traveled between frames, we can calculate speed and display it on a graph or chart.

- Total area explored

We could divide the enclosure into sections and track which sections the salamander has visited to estimate how much of the area it explored.

- Build a web dashboard with live tracking statistics

We already have a React frontend and tracking data, so building a dashboard to display statistics should be achievable by the end of the quarter.
Stretch

- Heatmap to visually show how much time the mander spends in certain areas

We would need to track the centroid over time and build a visualization showing where the salamander spends the most time. This would be challenging but possible.

- Generate a salamander racing line

We could connect centroid points together to create a path showing the salamander's movement throughout the video. Making it look good and useful would take extra work.

- Track its movements to guess what it could be doing at any given time

We could create rules based on speed, movement patterns, and time spent in one area to guess whether the salamander is resting, exploring, hunting, etc. This would be difficult because it requires making assumptions about behavior.


# Impossible

- Analyze the salamander to find out what kind of species of salamander it is

This would likely require machine learning, training data, and a lot more computer vision work than we can realistically complete before the end of the quarter. It would be really cool, but it is outside the scope of our project timeline.
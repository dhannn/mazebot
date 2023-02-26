# `m_ai_zbot` - A Simple Maze-Solving Application

`m_ai_zbot` uses intelligent agent to solve the maze on a given file. It uses two simple algorithms to find the solution path -- depth-first search and breadth-first search.

# Dependencies
The following are needed to compile the application

1. Java Development Kit
1. Apache Ant (or your IDE)

# Build

## Using Apache Ant
1. Clone the repository.
1. Open command line.
1. Change the working directory of the command line to the cloned repository.
1. Enter the command `ant jar`.

## Using IDE
Simply run the main file in `core.App`. Make sure you include all files in the repository as a Java project.

# Usage
To use the program, do the following steps:

1. To open executable, enter `ant run`.
1. In the text field of the program, enter the directory and the file name of the maze. Click `Load`.
1. Choose a search algorithm for `m_ai_zbot`. Click `Start`.
1. Upon clicking, the animation plays with the following rules:
    - The intelligent agent starts at the **initial cell** indicated as **red**. 
    - The cells **to be visited** are indicated as **blue** while the **expanded** cells are indicated as **yellow**. 
    - The goal cell is indicated as **dark green**. 
    - The **path** is then reconstructed, represented by **light green**.
1. You may also pause the animation or advanced it one step.



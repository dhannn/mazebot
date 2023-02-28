import matplotlib.pyplot as plt
import pandas as pd
import globals as glb
import csv

def get_data(filename: str, algo: str):
    x = []
    y = []

    with open(filename, 'r') as csvfile:
        rows = csv.reader(csvfile, delimiter=',')

        for row in rows:
            if row[1] == algo:
                x.append(int(row[0]))
                y.append(float(row[2]))

    return x, y

def plot():

    summaries = [
        [glb.D_RUNTIME_SUMMARY, glb.S_RUNTIME_SUMMARY],
        [glb.D_EXPLORED_SUMMARY, glb.S_EXPLORED_SUMMARY],
        [glb.D_SOLUTION_SUMMARY, glb.S_SOLUTION_SUMMARY]
    ]
    
    summary_to_plot = {
        (glb.D_RUNTIME_SUMMARY, glb.S_RUNTIME_SUMMARY): glb.RUNTIME_PLOT,
        (glb.D_EXPLORED_SUMMARY, glb.S_EXPLORED_SUMMARY): glb.EXPLORED_PLOT,
        (glb.D_SOLUTION_SUMMARY, glb.S_SOLUTION_SUMMARY): glb.SOLUTION_PLOT,
    }

    TITLES = [
        'Runtime (in ms) of BFS and DFS per input size (n)',
        'Explored states of BFS and DFS per input size (n)', 
        'Total states in solution of BFS and DFS per input size (n)'
    ]

    LABEL_X = 'Input size (n)'
    LABEL_Y = ['Runtime (in ms)', 'Explored states', 'Total states in solution']

    for s, summary in enumerate(summaries):
        fig = plt.figure(figsize=[10, 8])
        fig.suptitle(TITLES[s])

        SUBTITLES=['(dense mazes)', '(sparse mazes)']

        for i, csvfile in enumerate(summary):
            ax = fig.add_subplot(2, 1, i + 1)
            dfs_x, dfs_y = get_data(csvfile, "Depth-First Search")
            bfs_x, bfs_y = get_data(csvfile, "Breadth-First Search")
            ax.plot(dfs_x, dfs_y, label='dfs', marker='o')
            ax.plot(bfs_x, bfs_y, label='bfs', marker='o')

            ax.set_xlabel(LABEL_X)
            ax.set_ylabel(LABEL_Y[s])

            ax.set_xscale('log', base=2)
            ax.set_yscale('log', base=2)
            ax.legend(['dfs', 'bfs'])

            ax.set_title(SUBTITLES[i])
        
        fig.set_tight_layout(True)
        
        plot_file = summary_to_plot[(summary[0], summary[1])]
        fig.savefig(plot_file)


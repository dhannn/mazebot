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
                y.append(float(row[3]))

    return x, y

def box_and_whiskers():
    attribs = ['runtime', 'num_explored', 'num_solution']

    df = pd.read_csv(glb.S_DATA_FILENAME)
    
    for attrib in attribs:

        size = 4

        while size <= 64:
            _df = df.loc[df['size'] == size]
            boxplot = _df.boxplot(figsize=(6,10), widths=(0.5, 0.5), by=['search_name'], column=[attrib])
                        
            boxplot.set_xlabel('Search Algorithm')
            boxplot.set_ylabel('Runtime (in ms)')

            plt.suptitle('')
            boxplot.set_title('')

            fig = boxplot.get_figure()
            fig.set_tight_layout(True)
            fig.savefig(glb.PLOT_DIR + "/" + attrib + str(size) + "_dist.png")
            size = size << 1


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

    LABEL_X = 'Input size (n)'
    LABEL_Y = ['Runtime (in ms)', 'Explored states', 'Total states in solution']

    for s, summary in enumerate(summaries):
        fig = plt.figure(figsize=[10, 8])

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
        
        fig.set_tight_layout(True)
        
        plot_file = summary_to_plot[(summary[0], summary[1])]
        fig.savefig(plot_file)
    
    box_and_whiskers()



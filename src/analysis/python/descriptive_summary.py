import pandas
import globals as glb

def generate():
    analyze(True)
    analyze(False)

def analyze(isSparse: bool):
    raw_data_csv = glb.S_DATA_FILENAME if isSparse else glb.D_DATA_FILENAME

    df = pandas.read_csv(raw_data_csv)
    df = df.sort_values(by=['search_name', 'size'])
    df = df.groupby(['size', 'search_name'])

    runtime = df['runtime']
    num_explored = df['num_explored']
    num_solution = df['num_solution']

    runtime_csv = runtime.agg(['mean','std']).to_csv()
    num_explored_csv = num_explored.agg(['mean','std']).to_csv()
    num_solution_csv = num_solution.agg(['mean','std']).to_csv()

    csvs = [runtime_csv, num_explored_csv, num_solution_csv]

    filename_dict = {
        runtime_csv: glb.S_RUNTIME_SUMMARY if isSparse else glb.D_RUNTIME_SUMMARY,
        num_explored_csv: glb.S_EXPLORED_SUMMARY if isSparse else glb.D_EXPLORED_SUMMARY,
        num_solution_csv: glb.S_SOLUTION_SUMMARY if isSparse else glb.D_SOLUTION_SUMMARY
    }

    for csv in csvs:
        filename = filename_dict[csv]
        f = open(filename, "w", newline='\n')
        f.write(csv)
        f.close()

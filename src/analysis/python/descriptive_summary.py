import pandas
import globals

def generate():
    df = pandas.read_csv(globals.SAMPLE_DATA_FILENAME)
    df = df.sort_values(by=['search_name', 'size'])
    df = df.groupby(['size', 'search_name'])

    runtime = df['runtime']
    num_explored = df['num_explored']
    num_solution = df['num_solution']

    runtime_csv = runtime.describe().to_csv()
    num_explored_csv = num_explored.describe().to_csv()
    num_solution_csv = num_solution.describe().to_csv()

    csvs = [runtime_csv, num_explored_csv, num_solution_csv]

    filename_dict = {
        runtime_csv: globals.RUNTIME_SUMMARY_FILENAME,
        num_explored_csv: globals.EXPLORED_SUMMARY_FILENAME,
        num_solution_csv: globals.SOLUTION_SUMMARY_FILENAME
    }

    for csv in csvs:
        filename = filename_dict[csv]
        f = open(filename, "w", newline='\n')
        f.write(csv)
        f.close()

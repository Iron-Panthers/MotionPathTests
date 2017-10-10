from cx_Freeze import setup, Executable

base = None

executables = [Executable("pathCreator.py", base=base)]

packages = ['math']
options = {
	'build_exe': {
		'packages':packages,
	},
}

setup(name="Path Creator",options=options,version=1,description="Creates a path from given input. KNOWN ISSUE: If target is too close, causes bugs", executables = executables)
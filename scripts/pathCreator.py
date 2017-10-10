"""
[t0..t1] ACCEL: constant acceleration (a_max) from v = 0 to v_max; omega = 0 
[t1..t2] SLEW: v = v_max; omega = 0
[t2..t3] ENTER_TURN: v = v_max; constant angular acceleration (alpha_max) from omega = 0 to omega_max
[t3..t4] HOLD_TURN: v = v_max; omega = omega_max
[t4..t5] EXIT_TURN: v = v_max; constant angular deceleration (-alpha_max) from omega = omega_max to 0
[t5..t6] SLEW: v = v_max; omega = 0
[t6..t7] DECEL: constant deceleration (-a_max) from v = v_max to 0; omega = 0 

Contants: v_max, a_max,  omega_max, alpha_max, t1, t2, t3, t4, t5, t6, t7 are selected to generate the desired path and dynamic performance.
Make a spreadsheet or Python script to generate these constants from goals such as:
destination coordinates, total trajectory time, etc.

The outputs of the trajectory (v, omega) are transformed into wheel speed commands (v1_cmd, v2_cmd) using vehicle geometry (H=lateral spacing between wheels, and r=wheel radius):

v1_cmd(t) = (v(t) + omega(t) * H / 2) / r
v2_cmd(t) = (v(t) - omega(t) * H / 2) / r

"""
import math

ARCLENGTH_NUM_SAMPLES = 10000
INPUT = True

ACCEL_MAX = 25
V_MAX = 5
ALPHA_MAX = 5
OMEGA_MAX = 15
DEGREES = True

target_x = 250
target_y = 250
target_theta = 60

if INPUT:
	ACCEL_MAX = float(input("Enter maximum acceleration: "))
	V_MAX = float(input("Enter maximum velocity: "))
	ALPHA_MAX = float(input("Enter maximum alpha (angular acceleration): "))
	OMEGA_MAX = float(input("Enter maximum omega (angular velocity): "))
	DEGREES = input("Enter whether or not your angular units are degrees (y/N): ").startswith("y")

	target_x = float(input("Enter target x position: "))
	target_y = float(input("Enter target y position: "))
	target_theta = float(input("Enter target theta position: "))

target_destination = {'x':target_x, 'y':target_y, 'theta':target_theta} #(x,y,theta)
steps = [] # dicts, upload step results here

# EVERY STEP RETURNS A DICTIONARY WITH DeltaS,V0,V,A,T
def makeDict(s,dtheta,v0,v,a,o0,o,alpha,t,dx,dy):
	# Theta of 0 is actually a y change
	return {'ds':s, 'dx':dx, 'dy':dy, 'dtheta': dtheta, 'v0':v0, 'v':v, 'a':a, 'o0':o0, 'o':o, 'alpha':alpha, 't':t}
# Function for converting a theta to radians, unless already in radians
def convertToRadians(theta):
	if DEGREES:
		return theta * math.pi / 180.0
	return theta
# Find dx and dy
def findDeltasX(x, theta):
	dx = x * math.sin(convertToRadians(theta)) # Uses sin for x, +y is forward @ theta = 0
	dy = x * math.cos(convertToRadians(theta)) # Uses cos for y, +y is forward @ theta = 0
	return dx,dy
def findDeltas(v, time, theta):
	return findDeltasX(v * time, theta)
def findDeltasA(v0, time, accel, theta):
	kNum = ARCLENGTH_NUM_SAMPLES
	diterT = time / kNum
	iterT = 0
	v = v0
	dx = 0
	dy = 0
	while iterT < time:
		iterT += diterT
		v += accel * diterT
		cdx, cdy = findDeltas(v, diterT, theta)
		# print(v)
		dx += cdx
		dy += cdy
	return dx,dy
# Function for calculating arclength of the turn section
def findLength(v, time, omega, theta0):
	s = v * time
	kNum = ARCLENGTH_NUM_SAMPLES
	diterT = time / kNum
	iterT = 0
	dx = 0
	dy = 0
	while iterT < time:
		iterT += diterT
		theta = omega * iterT + theta0
		cdx, cdy = findDeltas(v, diterT, theta)
		dx += cdx
		dy += cdy
	return s, dx, dy
# Function for calculating arclength of the turn section, WITH angular acceleration
def findLengthA(v, time, omega0, alpha, theta0):
	s = v * time
	kNum = ARCLENGTH_NUM_SAMPLES
	diterT = time / kNum
	iterT = 0
	omega = omega0
	dx = 0
	dy = 0
	while iterT < time:
		iterT += diterT
		
		theta = omega * iterT + 0.5 * alpha * diterT ** 2 + theta0 # Kinematic equation for theta while accelerating
		omega += alpha * diterT
		cdx, cdy = findDeltas(v, diterT, theta)
		dx += cdx
		dy += cdy
	return s, dx, dy
# ACCEL step
def ACCEL(a_max, v0, vf, theta):
	deltaT = (vf - v0) / a_max
	s = .5 * a_max * deltaT** 2 + v0 * deltaT
	# print(deltaT)
	dx,dy = findDeltasA(v0, deltaT, a_max, theta)
	return makeDict(s, 0, v0, vf, a_max, 0, 0, 0, deltaT, dx, dy)
# SLEW step
def SLEW(v_max, deltaT, theta0):
	sC = v_max * deltaT
	dx, dy = findDeltas(v_max, deltaT, theta0)
	print("VMax: "+str(v_max)+"\tS: "+str(sC)+"\tT: "+str(deltaT)+"\tdx: "+str(dx)+"\tdy: "+str(dy))
	return makeDict(sC, 0, v_max, v_max, 0, 0, 0, 0, deltaT, dx, dy)
# ENTER_TURN step
def ENTER_TURN(v, alpha, omega0, omegaf):
	deltaT = (omegaf - omega0) / alpha
	dtheta = .5 * alpha * deltaT ** 2 + omega0 * deltaT
	theta0 = 0
	if alpha < 0:
		theta0 = dtheta
	# Calculate the arclength while turning
	s, dx, dy = findLengthA(v, deltaT, omega0, alpha, theta0)
	return makeDict(s, dtheta, v, v, 0, omega0, omegaf, alpha, deltaT, dx, dy)
# HOLD_TURN
def HOLD_TURN(v, omega, time, theta0):
	# Calculate the arclength while turning
	s, dx, dy = findLength(v, time, omega, theta0)
	return makeDict(s, omega * time, v, v, 0, omega, omega, 0, time, dx, dy)
# This is how to get the actual velocities to feed to the wheels. GIGANTIC piecewise function
def getVelsAtTime(currentTime, steps):
	t0 = steps[0]['t']
	t1 = steps[1]['t'] + t0
	t2 = steps[2]['t'] + t1
	t3 = steps[3]['t'] + t2
	t4 = steps[4]['t'] + t3
	t5 = steps[5]['t'] + t4
	t6 = steps[6]['t'] + t5
	# H = Width of robot
	# r = Radius of wheel
	# In this case, omega MIGHT be in radians/s...
	# v1_cmd(t) = (v(t) + omega(t) * H / 2) / r
	# v2_cmd(t) = (v(t) - omega(t) * H / 2) / r
	v = 0
	omega = 0

	if currentTime < t0:
		# First step velocity here
		v = steps[0]['a'] * currentTime
		omega = 0
	elif currentTime < t1:
		# Second step velocity here
		v = steps[1]['v']
		omega = 0
	elif currentTime < t2:
		# Third step here
		v = steps[2]['v']
		omega = convertToRadians(steps[2]['alpha'] * (currentTime - t1)) # delta time; because accel is in delta time
	elif currentTime < t3:
		# 4th
		v = steps[3]['v']
		omega = convertToRadians(steps[3]['o'])
	elif currentTime < t4:
		# 5th
		v = steps[4]['v']
		omega = convertToRadians(steps[4]['o0'] + steps[4]['alpha'] * (currentTime - t3)) # alpha is negative; delta time; because accel is in delta time
	elif currentTime < t5:
		# 6th
		v = steps[5]['v']
		omega = 0
	elif currentTime < t6:
		# Lastly, 6th
		v = steps[6]['v0'] + steps[6]['a'] * (currentTime - t5) # accel is negative; delta time; because accel is in delta time
		omega = 0
	elif currentTime - DELTA_TIME < t6:
		v = 0
		omega = 0
	else:
		# Uhm... WTF? exit NOW 
		raise TypeError
	vL = (v + omega * WIDTH / 2) / RADIUS
	vR = (v - omega * WIDTH / 2) / RADIUS
	return vL, vR
# Easy steps first:
step0 = ACCEL(ACCEL_MAX, 0, V_MAX, 0)
step6 = ACCEL(-ACCEL_MAX, V_MAX, 0, target_theta)
# If target_arclength is too large, change it here
step2 = ENTER_TURN(V_MAX, ALPHA_MAX, 0, OMEGA_MAX)
step4 = ENTER_TURN(V_MAX, -ALPHA_MAX, OMEGA_MAX, 0)
if abs(target_theta) < OMEGA_MAX ** 2 / ALPHA_MAX:
	# This means step2 and step4 need to be modified, as the theta change isn't enough to reach max omega
	dtheta = abs(target_theta) / 2
	timeturn = math.sqrt(2 * dtheta / ALPHA_MAX)
	new_omega = timeturn * ALPHA_MAX
	step2 = ENTER_TURN(V_MAX, ALPHA_MAX, 0, new_omega)
	step4 = ENTER_TURN(V_MAX, -ALPHA_MAX, new_omega, 0)

step3_theta = target_theta - step2['dtheta'] - step4['dtheta'] # target_theta - deltaTheta - deltaTheta (or dTheta * 2)
step3_time = step3_theta / OMEGA_MAX
step3 = HOLD_TURN(V_MAX, OMEGA_MAX, step3_time, step2['dtheta'])

combinedDx = step0['dx'] + step2['dx'] + step3['dx'] + step4['dx'] + step6['dx']
combinedDy = step0['dy'] + step2['dy'] + step3['dy'] + step4['dy'] + step6['dy']

deltaDx = target_x - combinedDx
step5dx = deltaDx
if target_theta != 0:
	ratio = 1.0 / math.tan(convertToRadians(target_theta)) # cot because cos is for y (heading)
else:
	ratio = 0
step5dy = step5dx * ratio # x only changes in one place: step 1, but y changes in both.
# Therefore, the combinedDy is changed here, as well as there.
# This above formula seems to be incorrect
combinedDy += step5dy
deltaDy = target_y - combinedDy # Includes step 5
print("ratio: "+str(ratio))
# print(convertToRadians(target_theta))
print(step5dx)
print(step5dy)
print("Step 1 Input: "+str(deltaDy))

step1_s = deltaDy
step5_s = math.sqrt(step5dx**2 + step5dy**2)
print("Step 5 Input: "+str(step5_s))

# Because step1 has no delta x change throughout the motion, ONLY step5 needs to be observed.
# Basically, deltaDx = step5_dx
# However! If deltaDx is 0, then deltaDy is the step amount
# Using just step5, step1 AND step5 is double the arclength of step5

step1_time = step1_s / V_MAX
print("S1 T: "+str(step1_time))
step5_time = step5_s / V_MAX
print("S5 T: "+str(step5_time))

step1 = SLEW(V_MAX, step1_time, 0) # first half of motion here
step5 = SLEW(V_MAX, step5_time, target_theta) # second half here (after turn)

steps.append(step0)
steps.append(step1)
steps.append(step2)
steps.append(step3)
steps.append(step4)
steps.append(step5)
steps.append(step6)

cumDx = 0
cumDy = 0
cumDs = 0
totalTime = 0

for i in range(len(steps)):
	d = steps[i]
	print("------------------\nStep: "+str(i))
	for k, v in d.items():
		print(k+": "+"%.2f" % v)
	cumDx += d['dx']
	cumDy += d['dy']
	cumDs += d['ds']
	totalTime += d['t']
print()
print("Cumulative dx: %.2f\tTarget dx: %.2f" % (cumDx, target_x))
print("Cumulative dy: %.2f\tTarget dy: %.2f" % (cumDy, target_y))
print("Cumulative ds: %.2f" % cumDs)
print("Total time: %.2f" % totalTime)
"""
LOGS
dx 			dy 			ds 			position	A,V,Alpha,Omega,theta
dx: 34.78	dy: 77.53	ds: 91.27 (50,50)		25,5,5,15,50
dx: 64.57	dy: 141.43	ds: 169.05 (100,100)	25,5,5,15,50
dx: 94.37	dy: 205.32	ds: 246.84 (150,150)	25,5,5,15,50
dx: 124.16	dy: 269.21	ds: 324.63 (200,200)	25,5,5,15,50
dx: 153.95	dy: 333.11	ds: 402.41 (250,250)	25,5,5,15,50
IT WORKS!!!
NEXT: TO ADD JAVA OUTPUT
"""
DELTA_TIME = 0.02
OUTPUT_FILE_NAME = "OutPath.java"
WIDTH = 40
RADIUS = 4
if INPUT:
	DELTA_TIME = float(input("Enter delta time (same unit as velocity measurements): ")) # This should be same unit as accelerations and velocities. In this case, seconds
	OUTPUT_FILE_NAME = input("Enter output file (including extension): ")
	WIDTH = float(input("Enter the width of the robot: "))
	RADIUS = float(input("Enter the radius of a wheel on the robot: "))
if OUTPUT_FILE_NAME.endswith(".java"):
	f = open(OUTPUT_FILE_NAME, "w")
	className = OUTPUT_FILE_NAME.split(".java")[0]
	f.write("package org.usfirst.frc.team5026.util.motionprofile;\n\n")
	f.write("/* PARAMS:\n")
	f.write(" * MAX_ACCELERATION: "+str(ACCEL_MAX)+"\n")
	f.write(" * MAX_VELOCITY: "+str(V_MAX)+"\n")
	f.write(" * MAX_ALPHA: "+str(ALPHA_MAX)+"\n")
	f.write(" * MAX_OMEGA: "+str(OMEGA_MAX)+"\n")
	f.write(" * TARGET X: "+str(target_x)+"\n")
	f.write(" * TARGET Y: "+str(target_y)+"\n")
	f.write(" * TARGET THETA: "+str(target_theta)+"\n")
	f.write(" * ARCLENGTH: "+str(cumDs)+"\n")
	f.write(" * TOTAL TIME: "+str(totalTime)+"\n")
	f.write(" * DELTA TIME: "+str(DELTA_TIME)+"\n")
	f.write(" * ROBOT WIDTH: "+str(WIDTH)+"\n")
	f.write(" * ROBOT WHEEL RADIUS: "+str(RADIUS)+"\n")
	f.write(" * ARCLENGTH NUM SAMPLES: "+str(ARCLENGTH_NUM_SAMPLES)+"\n")
	f.write(" */\n\n")
	f.write("public class "+className+" extends Path {\n")
	length = int(totalTime / DELTA_TIME) + 1
	f.write("\tpublic double[][] points = new double["+str(length)+"][2];\n")
	currentTime = DELTA_TIME
	i = 0
	# Has a buffer, just in case
	# Create methods to bypass 65535 limit
	charsPerLine_max_guess = 100
	characters = length * charsPerLine_max_guess
	lines_per_method = int(65535/charsPerLine_max_guess) - 2
	methodCount = 1
	while characters >= 65535:
		methodCount += 1
		characters -= lines_per_method * charsPerLine_max_guess
	line = 1
	for m in range(methodCount):
		f.write("\tprivate void fill"+str(m)+"() {\n")
		for l in range(lines_per_method):
			currentTime = line * DELTA_TIME
			try:
				vels = getVelsAtTime(currentTime, steps)
				f.write("\t\tdouble[] temp"+str(line-1)+" = {"+str(vels[0])+","+str(vels[1])+"};\n")
				f.write("\t\tpoints["+str(line-1)+"] = temp"+str(line-1)+";\n")
				line += 1
			except TypeError:
				# This means that this method is complete!
				# Ran out of lines in the path
				break
		f.write("\t}\n")

	# Create constructor
	f.write("\tpublic "+className+"() {\n")
	# Call methods:
	for j in range(methodCount):
		f.write("\t\tfill"+str(j)+"();\n")
	f.write("\t\tsuper.points = points;\n");
	f.write("\t}\n")
	# while currentTime < totalTime + DELTA_TIME:
	# 	vels = getVelsAtTime(currentTime, steps)
		
	# 	f.write("\t\tpoints["+str(i)+"] = {"+str(vels[0])+","+str(vels[1])+"};")
	# 	if currentTime + DELTA_TIME >= totalTime + DELTA_TIME:
	# 		f.write("\n") # Last point has no comma
	# 	else:
	# 		f.write(",\n")
	# 	# f.write("\t\ttemp"+str(i)+".add((double) "+str(vels[0])+");\n")
	# 	# f.write("\t\ttemp"+str(i)+".add((double) "+str(vels[1])+");\n")
	# 	# f.write("\t\tpoints.add(temp"+str(i)+");\n")
	# 	currentTime += DELTA_TIME
	# 	i += 1
	# f.write("\t}\n")
	f.write("}")
	f.close()
else:
	print("Sorry! This is not yet supported! Using default output format...")

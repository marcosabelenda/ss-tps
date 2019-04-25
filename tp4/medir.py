#!/usr/bin/python3
import subprocess
import os
import matplotlib.pyplot as plt
import numpy as np

CONF_FILE = 'configuration.txt'

M_LINE = 3 #menos uno
M_STRING = "cell_per_line:"

N_LINE = 1
N_STRING = "number_particles:"

def execute_process(cmd):
	p = subprocess.check_call(cmd.split())


def leo_archivo(filename):
	with open(filename,'r') as file:
		return file.readlines()

def edito_n_linea(filename,n,valor):
	lineas = leo_archivo(filename)
	lineas[n] = valor+'\r\n'
	with open(filename, 'w') as file:
		file.writelines(lineas)

def adivino_filename(n,m,PCC):
	string = 'metrics.txt'
#	string = 'statical-data-' + '1' + '-' + str(n) + '-' + '20' + '-' + str(20//m) + '-' + '1' +  '-' + 'true' + '.txt'
	return string

numeros=[20,50,100,200,500,1000]
def main():
	fig,ax = plt.subplots()
	for x in range(10):
		execute_process("java -cp tp2-1.0-SNAPSHOT.jar ar.edu.itba.ss.App")
		metricas = leo_archivo('metrics.txt')
		d_m = [float(k) for k in metricas]
#		xi = np.linspace(0,1,100)
		xi = list(range(1001))
		yi = d_m
		yp =  np.interp(xi,yi,xi)
		ax.plot(xi,yi)
	plt.show()

main()

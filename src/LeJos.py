from pathlib import Path
from typing import List

import subprocess

import sys

import os


def find_sources() -> List[str]:
    all_files = list()
    for root, dirs, files in os.walk('.'):
        for file in files:
            if file.endswith('.java'):
                all_files.append(file)
    return all_files


def find_compiled() -> List[str]:
    all_files = list()
    for root, dirs, files in os.walk('.'):
        for file in files:
            if file.endswith('.class'):
                all_files.append(file.replace('.class', ''))
    return all_files


def compile_all():
    files = find_sources()
    for file in files:
        compile(file)


def compile(source_file: str):
    proc = subprocess.Popen('powershell.exe', stdin=subprocess.PIPE, stdout=subprocess.PIPE)
    output = proc.communicate(f'nxjc "{str(source_file)}"'.encode(sys.getfilesystemencoding()))
    return output


def load(program: str, run: bool = True):
    print(f'Loading {program} and running is set on {run}')
    proc = subprocess.Popen('powershell.exe', stdin=subprocess.PIPE, stdout=subprocess.PIPE)
    run_str = ''
    if run is True:
        run_str = '-r '
    output = proc.communicate(f'nxj {run_str}"{str(program)}"'.encode(sys.getfilesystemencoding()))
    return output


compile_all()
compiled = find_compiled()
load('Main')

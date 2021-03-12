#include "stdafx.h"
#include <iostream>
#include <vector>
#include <fstream>
#include <windows.h>
#include <algorithm>

using namespace std;

void input(vector<vector<int>>& matrix)
{
	ifstream fin("input.txt");

	if (!fin.is_open())
	{
		cerr << "File input.txt not found\n";
		system("pause");
		exit(1);
	}

	int N, M;
	fin >> N >> M;
	matrix.resize(N, vector<int>(M));

	for (int i = 0; i < N; i++)
	{
		vector<int> temp;
		for (int j = 0; j < M; j++)
		{
			int cur;
			fin >> cur;
			temp.push_back(cur);
		}
		matrix[i] = temp;
	}

	fin.close();
}

void output(const vector<int>& flags)
{
	for (int i = 0; i < flags.size(); i++)
	{
		cout << i + 1 << " - " << flags[i] << endl;
	}
}

vector<int> bfs(const vector<vector<int>>& m, const int v) {
	//Breadth-First search
	vector<int> flags(m.size(), -1);
	flags[v] = 0;
	//Массив с метками. i = вершина, flags[i] = метка.

	for (int i = 0; i < m.size(); ++i) {
		for (int j = 0; j < flags.size(); ++j) {
			//Идем по массиву flags
			if (flags[j] == i) {
				//Находим крайнюю метку, номер которой равен i
				for (int k = 0; k < m.size(); ++k) {
					//Для всех смежных ей вершин, с не проставленными метками, ставим метку i+1
					if (m[j][k] == 1 && flags[k] == -1) flags[k] = i + 1;
				}
			}
		}
	}

	return flags;
}

bool connected_check(const vector<int>& v) {
	for (int i = 0; i < v.size(); ++i) {
		if (v[i] == -1) return false;
	}
	return true;
}

int remoteness_check(const vector<int>& v) {
	return *max_element(v.begin(), v.end());
}

int main()
{
	vector<vector<int>> matrix;
	input(matrix);

	int start;
	cout << "Input start vertex: \n";
	cin >> start;
	int finish;
	cout << "Input finish vertex: \n";
	cin >> finish;
	
	vector<int> path;
	path.push_back(finish - 1);
	auto flags = bfs(matrix, start - 1);

	for (int i = (finish - 1); i != (start - 1);) {
		int min_flag = 1000000;
		int min_v;
		for (int j = 0; j < matrix.size(); ++j) {
			if (matrix[i][j] == 1 && flags[j] < min_flag) {
				min_flag = flags[j];
				min_v = j;
			}
		}
		path.push_back(min_v);
		i = min_v;
	}

	for (int i = path.size()-1; i >= 0; --i) {
		cout << path[i]+1 << ' ';
	}
	system("pause");
	return 0;
}

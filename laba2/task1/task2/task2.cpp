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

	if (!connected_check(bfs(matrix, 0))) {
		system("pause");
		exit(1);
	}

	int min = 100000;
	int max = -1;
	vector<int> remoteness;
	for (int i = 0; i < matrix.size(); ++i) {
		int tmp = remoteness_check(bfs(matrix, i));
		remoteness.push_back(tmp);
		cout << i + 1 << " - " << tmp << endl;
		if (tmp < min) min = tmp;
		if (tmp > max) max = tmp;
	}

	cout << "Radius: " << min << endl;
	cout << "Diametr: " << max << endl;

	vector<int> center(matrix.size());
	vector<int> p_center(matrix.size());
	for (int i = 0; i < matrix.size(); ++i) {
		if (remoteness[i] == min) center[i] = 1;
		if (remoteness[i] == max) p_center[i] = 1;
	}

	cout << "Centers: ";
	for (int i = 0; i < matrix.size(); ++i) {
		if (center[i] == 1)  cout << i + 1 << " ";
	}
	cout << endl;

	cout << "P_centers: ";
	for (int i = 0; i < matrix.size(); ++i) {
		if (p_center[i] == 1)  cout << i + 1 << " ";
	}
	cout << endl;

	system("pause");
	return 0;
}

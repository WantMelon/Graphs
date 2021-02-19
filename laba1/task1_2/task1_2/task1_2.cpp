#include <iostream>
#include <fstream>
#include <vector>
#include <iomanip>

using namespace std;

void input(vector<vector<int>>& inc_matrix)
{
	ifstream fin("input.txt");

	if (!fin.is_open())
	{
		cerr << "File input.txt not found\n";
		exit(1);
	}

	int N, M;
	fin >> N >> M;
	inc_matrix.resize(N, vector<int>(M));

	for (int i = 0; i < N; i++)
	{
		vector<int> temp;
		for (int j = 0; j < M; j++)
		{
			int cur;
			fin >> cur;
			temp.push_back(cur);
		}
		inc_matrix[i] = temp;
	}

	fin.close();
}

void output(vector<vector<int>>& matrix)
{
	for (int i = 0; i < matrix.size(); i++)
	{
		for (int j = 0; j < matrix[i].size(); j++)
		{
			cout << setw(3) << matrix[i][j] << ' ';
		}
		cout << endl;
	}
}

void foo(const vector<vector<int>>& inc_matrix, vector<vector<int>>& adj_matrix)
{
	const int N = inc_matrix.size();
	adj_matrix.resize(N, vector<int>(N));

	for (int j = 0; j < inc_matrix[0].size(); j++)
	{
		int head, tail;
		for (int i = 0; i < inc_matrix.size(); i++)
		{
			if (inc_matrix[i][j] == -1)	head = i;
			if (inc_matrix[i][j] == 1) tail = i;
		}
		adj_matrix[head][tail] = 1;
	}
}

int main()
{
	vector<vector<int>> inc_matrix;
	vector<vector<int>> adj_matrix;
	input(inc_matrix);

	foo(inc_matrix, adj_matrix);

	output(adj_matrix);

	return 0;
}
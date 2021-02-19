#include <iostream>
#include <fstream>
#include <vector>

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
	inc_matrix.resize(N, vector<int> (M));

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
			cout << matrix[i][j] << ' ';
		}
		cout << endl;
	}
}

void foo(const vector<vector<int>>& inc_matrix, vector<vector<int>>& adj_matrix)
{
	const int N = inc_matrix.size();
	adj_matrix.resize(N, vector<int> (N));
	
	for (int j = 0; j < inc_matrix[0].size(); j++)
	{
		vector<int> temp;
		for (int i = 0; i < inc_matrix.size(); i++)
		{
			if (inc_matrix[i][j] == 1)	temp.push_back(i);
		}
		adj_matrix[temp[0]][temp[1]] = 1;
		adj_matrix[temp[1]][temp[0]] = 1;
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
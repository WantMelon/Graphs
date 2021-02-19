#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

void input(vector<vector<int>>& adj_matrix)
{
	ifstream fin("input.txt");

	if (!fin.is_open())
	{
		cerr << "File input.txt not found\n";
		exit(1);
	}

	int N, M;
	fin >> N >> M;
	adj_matrix.resize(N, vector<int>(M));

	for (int i = 0; i < N; i++)
	{
		vector<int> temp;
		for (int j = 0; j < M; j++)
		{
			int cur;
			fin >> cur;
			temp.push_back(cur);
		}
		adj_matrix[i] = temp;
	}

	fin.close();
}

void output(vector<int>& power_seq)
{
	for (int i = 0; i < power_seq.size(); i++)
	{
		cout << power_seq[i] << ' ';
	}
	cout << endl;
}

void foo(vector<vector<int>>& adj_matrix, vector<int>& power_seq)
{
	power_seq.resize(adj_matrix.size());

	for (int i = 0; i < adj_matrix.size(); i++)
	{
		for (int j = 0; j < adj_matrix.size(); j++)
		{
			if (adj_matrix[i][j] == 1)	power_seq[i]++;
		}
	}
}

int main()
{
	vector<vector<int>> adj_matrix;
	vector<int> power_seq;

	input(adj_matrix);

	foo(adj_matrix, power_seq);

	output(power_seq);

	system("pause");
	return 0;
}
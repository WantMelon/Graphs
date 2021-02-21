#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>
#include <string>

using namespace std;

void input(vector<vector<int>>& adj_matrix, string file_name)
{
	ifstream fin(file_name);

	if (!fin.is_open())
	{
		cerr << "File " << file_name << " not found\n";
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

void output_seed(const vector<int>& seed)
{
	for (int i = 0; i < seed.size(); i++)
	{
		cout << i + 1 << " - " <<  seed[i] + 1 << endl;
	}
}

vector<int> power_seq(const vector<vector<int>>& m)
{
	vector<int> v_power_seq;

	v_power_seq.resize(m.size());

	for (int i = 0; i < m.size(); i++)
	{
		for (int j = 0; j < m.size(); j++)
		{
			if (m[i][j] == 1) v_power_seq[i]++;
		}
	}

	return v_power_seq;
}

int v_counter(const vector<vector<int>>& m)
{
	return m.size();
}

int e_counter(const vector<vector<int>>& m)
{
	int counter = 0;

	for (int i = 0; i < m.size(); ++i)
		for (int j = 0; j < m[0].size(); ++j)
			if (m[i][j] == 1) counter++;

	return (counter / 2);
}

int factorial(int n)
{
	int res = 1;
	while (n != 1) res *= n--;

	return res;
}

vector<vector<int>> alternative_matrix(const vector<int>& seed, const vector<vector<int>>& m)
{
	vector<vector<int>> new_matrix = m;
	for (int i = 0; i < seed.size(); ++i)
	{
		for (int j = 0; j < m.size(); ++j)
		{
			if (m[j][i] == 1)
			{
				new_matrix[seed[j]][seed[i]] = 1;
				new_matrix[seed[i]][seed[j]] = 1;
			}
		}
	}

	return new_matrix;
}

bool isomorphism_check(const vector<vector<int>>& m1, const vector<vector<int>>& m2)
{
	if (v_counter(m1) != v_counter(m2)) return false;
	if (e_counter(m1) != e_counter(m2)) return false;
	if (power_seq(m1) != power_seq(m2)) return false;

	vector<int> seed(v_counter(m1));
	for (int i = 0; i < seed.size(); ++i)
		seed[i] = i;

	for (int i = 0; i <= factorial(m1.size()); ++i)
	{
		if (m1 == alternative_matrix(seed, m2))
		{
			output_seed(seed);
			return true;
		}
		next_permutation(seed.begin(), seed.end());
	}

	return false;
}

int main()
{
	system("color 02");

	vector<vector<int>> matrix1;
	vector<vector<int>> matrix2;

	input(matrix1, "matrix1.txt");
	input(matrix2, "matrix2.txt");

	if (isomorphism_check(matrix1, matrix2)) cout << "Graphs are isomorphic!\n";
	else cout << "Graphs are NOT isomorphic!\n";

	system("pause");
	return 0;
}
#include <iostream>
#include <vector>
#include <fstream>

using namespace std;

void input(vector<vector<int>>& matrix)
{
	ifstream fin("input.txt");

	if (!fin.is_open())
	{
		cerr << "File input.txt not found\n";
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
		cout << i+1 << " - " << flags[i] << endl;
	}
}

vector<int> bfs(const vector<vector<int>>& m, const int v) {
	//Breadth-First search
	vector<int> flags (m.size(), -1);
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

int main() 
{
	vector<vector<int>> matrix;
	input(matrix);

	int v;
	cout << "Input start vertex: \n";
	cin >> v;
	vector<int> flags = bfs(matrix, --v);

	output(flags);

	system("pause");
	return 0;
}
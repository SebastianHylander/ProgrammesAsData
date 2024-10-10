// Histogram (second time's the charm)

void histogram(int n, int ns[], int max, int freq[])
{
	int i;
	i = 0;
	while (i < n)
	{
		freq[ns[i]] = freq[ns[i]] +1;
		i = i + 1;
	}
}
void main()
{
	int max;
	max = 3;
	int arr[7];
	arr[0] = 1;
	arr[1] = 2;
	arr[2] = 1;
	arr[3] = 1;
	arr[4] = 1;
	arr[5] = 2;
	arr[6] = 0;
	int freq[4];

	int j;
	j = 0;
	while (j <= max)
	{
		freq[j] = 0;
		j = j + 1;
	}
	histogram(7, arr, 3, freq);
	
	int i;
	i = 0;
	while (i <= max)
	{
		print freq[i];
		i = i + 1;
	}
}

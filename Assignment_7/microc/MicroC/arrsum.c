// Computes and returns the sum of the first n elements of arr

// void arrsum(int n, int arr[], int *sump)
// {
// 	int i;
// 	i = 0;
// 	int sum;
// 	sum = 0;
// 	while (i < n)
// 	{
// 	sum = sum + arr[i];
// 	i = i +1;
// 	}
// 	*sump = sum;
// }

// void main()
// {
// 	int arr[4];
// 	arr[0] = 7;
// 	arr[1] = 13;
// 	arr[2] = 9;
// 	arr[3] = 8;
// 	int p;
// 	arrsum(4, arr, &p);
// 	print p;
// }

// arrsum with for loops instead of while

void arrsum(int n, int arr[], int *sump)
{
	int i;
	i = 0;
	int sum;
	sum = 0;

	for (i = 0; i < n; i = i + 1)
	{
	sum = sum + arr[i];
	}
	*sump = sum;
}

void main()
{
	int arr[4];
	arr[0] = 7;
	arr[1] = 13;
	arr[2] = 9;
	arr[3] = 8;
	int p;
	arrsum(4, arr, &p);
	print p;
}


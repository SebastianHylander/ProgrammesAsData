// fills arr[i] with i*i for i = 0, ..., n-1
// 
// void squares(int n, int arr[]){
    // int i; i = 0;
    // while (i < n){
        // arr[i] = i*i;
        // i = i+1;
    // }
// }
// 
// void main(int n) {
    // int arr[20];
    // squares(n, arr);
    // int i; i = 0;
    // while (i < n){
        // print arr[i];
        // i = i + 1;
    // }
// }
// 

// Squares with for loops instead of while
void squares(int n, int arr[]){
    int i; i = 0;

    for (i = 0; i < n; i = i + 1)
    {
        arr[i] = i*i;
    }
}

void main(int n) {
    int arr[20];
    squares(n, arr);
    int i; i = 0;

    for (i = 0; i < n; i = i + 1)
    {
        print arr[i];
    }

}
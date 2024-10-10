// fills arr[i] with i*i for i = 0, ..., n-1

void squares(int n, int arr[]){
    int i; i = 0;
    while (i < n){
        arr[i] = i*i;
        i = i+1;
    }
}

void main(int n) {
    int arr[20];
    squares(n, arr);
    int i; i = 0;
    while (i < n){
        print arr[i];
        i = i + 1;
    }
}

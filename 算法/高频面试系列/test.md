boolean canFinish(int[] w, int D, int cap) {
    int time = 0;
    int sum = 0;
    for(int i : w) {
        if(cap<(sum+i)){
            time++;
            break;
        }
        sum += i;
    }
    return time<=D;
    
    for (int day = 0; day < D; day++) {
        int maxCap = cap;
        while ((maxCap -= w[i]) >= 0) {
            i++;
            if (i == w.length)
                return true;
        }
    }
    return false;
}
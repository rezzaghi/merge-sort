use std::collections::VecDeque;
use std::thread;
use std::time::Instant;
use rand::Rng;


fn main() {

    let mut rng = rand::thread_rng();
    let mut list: VecDeque<i32> = VecDeque::new();

    for _ in 0..1000000 {
        list.push_back(rng.gen());
    }

    let time = Instant::now();
    println!("{:?}", merge_sort(list));
    let duration = time.elapsed();
    println!("time spent: {:?}", duration);
}

fn merge_sort(list: VecDeque<i32>) -> VecDeque<i32>{

    if list.len() <=1 {
        return list
    }

    let mut left:VecDeque<i32> = VecDeque::new();
    let mut right:VecDeque<i32> = VecDeque::new();
    for (index, value) in list.iter().enumerate() {
        if index < list.len()/2 {
            left.push_back(*value)
        }
        else{
            right.push_back(*value)
        }
    }

//single thread
    //left = merge_sort(left);
    //right = merge_sort(right);
//


// rayon version
// faster than others
let (left_sorted,right_sorted) = rayon::join(|| merge_sort(left), || merge_sort(right));
left = left_sorted;
right = right_sorted;
//


// std thread version
// slower than others
/*
    let job = thread::spawn(|| {
        left = merge_sort(left);
        right = merge_sort(right);
        (left, right)
    });

    let (left_sorted, right_sorted) = job.join().unwrap();

    left = left_sorted;
    right = right_sorted;
*/

    return merge(&mut left,&mut right)
}

fn merge(left: &mut VecDeque<i32>, right: &mut VecDeque<i32>) -> VecDeque<i32>{
    let mut result: VecDeque<i32> = VecDeque::new();

    while !left.is_empty() && !right.is_empty(){
        if left[0] <= right[0] {
            result.push_back(left[0]);
            left.pop_front();
        }
        else{
            result.push_back(right[0]);
            right.pop_front();
        }
    }

    while !left.is_empty(){
        result.push_back(left[0]);
        left.pop_front();
    }
    while !right.is_empty() {
        result.push_back(right[0]);
        right.pop_front();
    }

    return result

}
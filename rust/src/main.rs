use std::collections::VecDeque;
use std::fs::File;
use std::io::{self, BufRead};
use std::time::Instant;

fn main() -> io::Result<()> {

    let file = File::open("10000.txt")?;
    let reader = io::BufReader::new(file);

    let mut list: VecDeque<i32> = VecDeque::new();

    for line in reader.lines() {
        if let Ok(line) = line {
            if let Ok(number) = line.parse::<i32>() {
                list.push_front(number); 
            } else {
                println!("Failed to parse integer from line: {}", line);
            }
        }
    }

    let time = Instant::now();
    merge_sort(list);
    let duration = time.elapsed();
    println!("time spent: {:?}", duration);
    Ok(())
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

    let (mut left_sorted, mut right_sorted) = rayon::join(|| merge_sort(left), || merge_sort(right));

    return merge(&mut left_sorted,&mut right_sorted)
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
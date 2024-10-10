module Merge

let merge lst1 lst2 = 
    let rec _merge lst1 lst2 acc = 
        match lst1 with
        | [] -> (List.rev acc) @ lst2 
        | x :: xs -> 
            match lst2 with
            | [] -> (List.rev acc) @ lst1
            | y :: ys -> if x <= y then _merge xs lst2 (x :: acc) 
                                   else _merge lst1 ys (y :: acc)
    _merge lst1 lst2 []

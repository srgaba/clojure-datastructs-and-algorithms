(ns recursion.map-implementation)

;implementing a map with no return, just execution
(defn my-map
  [fn elements]
  (let [f (first elements)]
    (if (not (nil? f))
      (do
        (fn f)
        (recur fn (rest elements))))))

;(my-map println (range 100000))

;implementing a map with return
(defn not-nil? [value]
  (not (nil? value)))

(println "\n\n a map with return")
(defn my-map
  [fc elements]
  (let [f (first elements)
        s (second elements)]
    (when (not-nil? f))
      (concat [(fc f)] (if (not-nil? s) (my-map fc (rest elements))))))

(println (my-map inc [1 2 3 4 5]))

;its not a good practice, so that we are not using the recur instruction

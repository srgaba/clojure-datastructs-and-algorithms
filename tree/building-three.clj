(ns run.test
  (:use [clojure pprint]))

(defn build-node
  [data]
  {:data data
   :left nil
   :right nil})

(defn build-three
  [node]
  {:head node
   :size 1})

(defn bigger-than-current-node?
  [{data-node-to-insert :data} {data-current-node :data}]
  (>= data-node-to-insert data-current-node))

(defn next-position-is-nil?
  [current-node position]
  (not (position current-node)))

(defn insert-node-in-given-position
  [current-node node-to-insert position]
  (assoc current-node position node-to-insert))

(defn travel-and-insert!
  [node-to-insert
   current-node]
  (let [dr-node-to-insert @node-to-insert
        dr-current-node @current-node]
    (if (bigger-than-current-node? dr-node-to-insert dr-current-node)
      (if (next-position-is-nil? dr-current-node :right)
        (swap! current-node insert-node-in-given-position node-to-insert :right)
        (recur node-to-insert (:right dr-current-node)))
      (if (next-position-is-nil? dr-current-node :left)
        (swap! current-node insert-node-in-given-position node-to-insert :left)
        (recur node-to-insert (:left dr-current-node))))))

(defn add-node!
  [node
   three]
  (let [head (:head @three)]
    (println head)
    (travel-and-insert! node head)))

(defn my-flow
  []
  (let [first-node (atom (build-node 1))
        three (atom (build-three first-node))
        new-node (atom (build-node 2))]
    (add-node! new-node three)
    (pprint three)
    ))

(my-flow)

(ns run.test
  (:use [clojure pprint]))

(defn build-node
  [value]
  {:data value
   :next nil})

(defn build-linked-list
  [initial-value]
  (let [initial-node (atom (build-node initial-value))]
    {:head initial-node
     :tail initial-node}))

(defn update-tail
  [ll node]
  (assoc ll :tail node))

(defn update-current-tail-to-point-to-the-new-node
  [current-tail-node new-node]
  (assoc current-tail-node :next new-node))

(defn append!
  [ll value]
  (let [dll @ll
        node (atom (build-node value))
        current-tail (get dll :tail)]
    (swap! current-tail update-current-tail-to-point-to-the-new-node node)
    (swap! ll update-tail node)
    ll))

(def ll (atom (build-linked-list 1)))

(append! ll 2)

(pprint ll)

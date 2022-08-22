(ns run.linked-list
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

(defn update-head-to-point-to-next-node
  [ll next-node]
  (assoc ll :head next-node))

(defn rm-head
  [ll]
  (let [dll @ll
        current-node-head (get dll :head)
        next-node (get @current-node-head :next)]
    (swap! ll update-head-to-point-to-next-node next-node)
    ll))

(defn get-the-previous-node-from-current-tail
  [node]
  (let [next-node (get @node :next)
        next-of-next-node-is-nil? (not (get @next-node :next))]
    (if next-of-next-node-is-nil?
      node
      (recur next-node))))

(defn update-tail-with-previous-node
  [ll previous-node]
  (assoc ll :tail previous-node))

(defn disconnect-node-to-another
  [node]
  (assoc node :next nil))

(defn rm-tail
  [ll]
  (let [dll @ll
        head-node (get dll :head)
        previous-node-from-tail (get-the-previous-node-from-current-tail head-node)]
    (swap! ll update-tail-with-previous-node previous-node-from-tail)
    (swap! previous-node-from-tail disconnect-node-to-another)
    ll))



(defn get-node-base-value
  [node value]
  (if node
    (let [dnode @node]
      (if (= (get dnode :data) value)
        node
        (recur (get dnode :next) value)))
    nil))

(defn get-node
  [ll value]
  (let [head-node (get @ll :head)]
    (get-node-base-value head-node value)))

(def ll (atom (build-linked-list 1)))

(append! ll 2)
(append! ll 3)
(append! ll 4)
(append! ll 5)

(rm-tail ll)

(pprint ll)
;(rm-head ll)

(println (get-node ll 0))

(ns run.tree
  (:use [clojure.pprint]))


(defn build-node
  [data]
  {:data data
   :left nil
   :right nil})

(defn build-three
  [node]
  {:head node
   :size 1})

(defn bigger-or-equal-to-current-node?
  [{data-node1 :data} {data-node2 :data}]
  (>= data-node1 data-node2))

(defn equal-nodes?
  [{data-node1 :data} {data-node2 :data}]
  (= data-node1 data-node2))

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
    (if (bigger-or-equal-to-current-node? dr-node-to-insert dr-current-node)
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
    (travel-and-insert! node head)))

(defn remove-node-in-given-position
  [current-node position]
  (assoc current-node position nil))

(defn get-next-node-to-travel
  [node
   current-node]
  (if (and (not (next-position-is-nil? current-node :right))
        (bigger-or-equal-to-current-node? node current-node))
    (:right current-node)
    (if (not (next-position-is-nil? current-node :left))
      (:left current-node)
      nil)))

(defn deref-in-given-position
  [current-node position]
  (when-let [node (position current-node)]
    @node))

;#todo improve this numbers of lines of codes?
;#todo logical of remove parent with children..
(defn travel-and-remove-node!
  [node-to-remove
   current-node]
  (let [dr-node-to-remove @node-to-remove
        dr-current-node @current-node
        dr-left-children-node (deref-in-given-position dr-current-node :left)
        dr-right-children-node(deref-in-given-position dr-current-node :right)]
    (if (and (not (next-position-is-nil? dr-current-node :left))
             (equal-nodes? dr-node-to-remove dr-left-children-node))
      (do (swap! current-node remove-node-in-given-position :left)
          true)
      (if (and (not (next-position-is-nil? dr-current-node :right))
            (equal-nodes? dr-node-to-remove dr-right-children-node))
        (do (swap! current-node remove-node-in-given-position :right)
            true)
        (if-let [next-node (get-next-node-to-travel dr-node-to-remove dr-current-node)]
          (recur node-to-remove next-node)
          false)))))

(defn remove-node!
  [node-to-remove
   three]
  (let [head (:head @three)
        dr-head @head
        dr-node-to-remove @node-to-remove]
    (if (equal-nodes? dr-head dr-node-to-remove)
      (println "traga o nó da esquerda ou direita... para cima")
      (travel-and-remove-node! node-to-remove head))))

;recursive, why?
(defn my-flow
  []
  (let [node-4  (atom (build-node 4))
        three (atom (build-three node-4))
        node-5 (atom (build-node 5))
        node-6 (atom (build-node 6))
        node-3 (atom (build-node 3))]
    (add-node! node-5 three)
    (add-node! node-6 three)
    (add-node! node-3 three)
    (remove-node! node-6 three)
    (remove-node! node-5 three)
    (pprint three)))

(my-flow)

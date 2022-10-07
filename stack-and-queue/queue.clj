(ns run.queue
  (:use [clojure pprint]))

(defn build-node [value]
  {:value value
   :next nil})

(defn build-queue []
  {:first nil
   :last nil
   :len 0})

(defn peek-f [queue]
  (pprint (:first @queue)))

(defn init-queue [queue new-node]
  (assoc queue :first new-node :last new-node :len 1))

(defn queue-is-empty? [queue]
  (= (:len queue) 0))

(defn point-to-next-node [current-node next-node]
  (assoc current-node :next next-node))

(defn assoc-queue! [{:keys [last len] :as queue} new-node]
  (swap! last point-to-next-node new-node)
  (assoc queue :last new-node :len (inc len)))

(defn enqueue! [queue value]
  (let [new-node (atom (build-node value))
        def-queue @queue]
    (if (queue-is-empty? def-queue)
      (swap! queue init-queue new-node)
      (swap! queue assoc-queue! new-node))))

(defn update-first-member [queue second len]
  (assoc queue :first second :len (dec len)))

(defn has-single-element? [queue]
  (= (:len queue) 1))

(defn clear-queue! [queue]
  (assoc queue :first nil :last nil :len 1))

(defn dequeue! [queue]
  (if (has-single-element? @queue)
    (swap! queue clear-queue!)
    (let [def-queue @queue
          second (:next @(:first def-queue))
          len (:len def-queue)]
      (swap! queue update-first-member second len))))

(defn flow []
  (let [queue (atom (build-queue))]
    (enqueue! queue 1)
    (enqueue! queue 2)
    (pprint @queue)
    (dequeue! queue)
    (dequeue! queue)
    (pprint @queue)))

(flow)
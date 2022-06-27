(ns run.core
  (:use [clojure.pprint]))

(defn build-array [max-length]
  (atom {:length 0 :max-length max-length}))

(defn is-full?
  [a]
  (= (:length @a) (:max-length @a)))

(defn push-to [a value]
  (if-not (is-full? a)
    (let [len (:length @a)]
      (swap! a assoc len value :length (+ len 1)))
    (throw (ex-info "array is full" {:value value}))))


(defn came-to-an-end [current end]
  (> current end))

(defn re-index [a current end]
  (if-not (came-to-an-end current end)
    (let [new-a (assoc a (- current 1) (get a current))]
      (recur new-a (inc current) end))
    (dissoc a end)))

(defn rm [a key]
   (-> a
        (dissoc key)
        (update :length dec)))

(defn value-is-equal-to-value-in-array [kv find]
  (= (val kv) find))

(defn rm-value! [a value]
  (let [derefed-a @a
        length (:length derefed-a)]
    (doseq [kv derefed-a]
      (when (value-is-equal-to-value-in-array kv value)
        (as-> derefed-a drfda
            (rm drfda (kv 0))
              (re-index drfda (+ (kv 0) 1) (- length 1))
            (reset! a drfda))))))

(defn rm-last-value [a]
  (let [last-index (- (:length @a) 1)]
    (reset! a (rm a last-index))))

(def a (build-array 5))

(defn simulate []
  (let [a (build-array 5)]
    (pprint a)
    (push-to a 1)
    (push-to a 2)
    (push-to a 3)
    (push-to a 4)
    (push-to a 5)
    (rm-value! a 2)
    (rm-value! a 3)
    (rm-value! a 1)
    (pprint a)))

(simulate)

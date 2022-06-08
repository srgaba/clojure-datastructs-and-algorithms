(defn build-array [max-length]
  (atom {:length 0 :max-length max-length}))

(defn push-to [a value]
  (if-not (= (:length @a) (:max-length @a)) 
    (let [len (:length @a)]
      (reset! a (assoc @a len value :length (+ len 1)))) 
    (println "full")))


(defn re-index-to [a start]
  (let [end (+ (:length @a) 1)]
    (doseq [i (range start end)]
      (reset! a (assoc @a (- i 1) (get @a i))))
    (reset! a (dissoc @a (- end 1)))))


(defn rm [a key]
  (let [new-len (- (:length @a) 1)] 
    (-> @a
      (dissoc key)
      (assoc :length new-len))))

(defn rm-last-value [a]
  (let [last-index (- (:length @a) 1)]
    (reset! a (rm a last-index))))


(defn rm-value [a value]
  (doseq [kv @a]
      (when (= (val kv) value)
        (reset! a (rm a (kv 0)))
        (re-index-to a (+ (kv 0) 1)))))

(def a (build-array 5))

(push-to a 1)
(push-to a 2)
(push-to a 3)
(push-to a 4)
(push-to a 5)

(rm-value a 3)
(rm-value a 2)

(rm-last-value a)

(push-to a 7)


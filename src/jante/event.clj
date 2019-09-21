(ns jante.event) 


(defmacro events
  [& args]
  (let [func (last args)]
    (def events 
      (reduce
        (fn [events event]
          (update events event #(conj % func)))
        events
        (-> (reverse args)
            (rest)
            (reverse))))))

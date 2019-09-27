(ns jante.event
  (:require [jante.plugin-namespaces]))
 
(defn get-functions-in-namespace
  [jante-namespace]
  (into [] (ns-publics jante-namespace)))

(defn get-all-triggered-by
  [event data]
  (reduce 
    (fn 
      filter-plugins
      [known-triggers [plugin-key plugin-namespace]]
      (->> (get-functions-in-namespace plugin-namespace) 
            (map second)
            (filter 
              (fn 
                      is-triggered 
                      [f]
                      (if-let [trigger-filter 
                               (get-in (meta f) [:events event])]
                        (trigger-filter data)
                        false)))
            (map #(list plugin-key %))
            (concat known-triggers)))
    '()
    jante.plugin-namespaces/namespaces))

(defn trigger-event
  [state event data])


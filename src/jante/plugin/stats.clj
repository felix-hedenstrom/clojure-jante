(ns jante.plugin.stats)

(defn listen
  {:events {:on-message (fn [data] true)}}
  [plugin-state data])

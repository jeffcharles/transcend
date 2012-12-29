(ns transcend.main
  (:use seesaw.core
        transcend.ui))

(defn -main
  [& args]
  (invoke-later
    (-> (frame :title "Transcend"
          :content (scrollable (create-table))
          :on-close :exit)
      pack!
      show!)))

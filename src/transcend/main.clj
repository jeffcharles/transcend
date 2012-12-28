(ns transcend.main
  (:use seesaw.core))

(defn -main
  [& args]
  (invoke-later
    (-> (frame :title "Transcend"
          :content ""
          :on-close :exit)
      pack!
      show!)))

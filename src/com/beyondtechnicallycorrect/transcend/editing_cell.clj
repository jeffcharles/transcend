(ns com.beyondtechnicallycorrect.transcend.editing-cell)

(def ^:private cell-being-edited (atom [nil nil]))

(defn get-cell-being-edited
  []
  @cell-being-edited)

(defn set-cell-being-edited!
  [row col]
  (swap! cell-being-edited (fn [cell] [row col])))

(defn set-no-cell-being-edited!
  []
  (swap! cell-being-edited (fn [cell] [nil nil])))

(ns com.beyondtechnicallycorrect.transcend.ui
  (:require
    [com.beyondtechnicallycorrect.transcend.table-model-adapter :as adapter]
    [com.beyondtechnicallycorrect.transcend.editing-cell
      :refer [set-cell-being-edited! set-no-cell-being-edited!]]))

(declare custom-table)
(declare create-table-cell-renderer)

(def ^:private first-col-width 50)

(defn create-table
  []
  (let [table (custom-table)
        first-col (-> (.getColumnModel table) (.getColumn 0))]
    (doto first-col
      (.setMaxWidth first-col-width)
      (.setMinWidth first-col-width)
      (.setPreferredWidth first-col-width)
      (.setResizable false))
    (doto table
      (-> .getTableHeader (.setReorderingAllowed false))
      (.setAutoResizeMode javax.swing.JTable/AUTO_RESIZE_OFF)
      (.setDefaultRenderer java.lang.Object (create-table-cell-renderer))
      (.setGridColor java.awt.Color/LIGHT_GRAY)
      (.setSelectionForeground java.awt.Color/BLACK))))

(defn- custom-table
  []
  (proxy [javax.swing.JTable] [(adapter/create-table-model)]
    (editCellAt
      ([row column]
        (set-cell-being-edited! row column)
        (proxy-super editCellAt row column))
      ([row column e]
        (set-cell-being-edited! row column)
        (proxy-super editCellAt row column e)))
    (editingCanceled
      [e]
      (set-no-cell-being-edited!)
      (proxy-super editingCanceled e))
    (editingStopped
      [e]
      (set-no-cell-being-edited!)
      (proxy-super editingStopped e))))

(defn- create-table-cell-renderer
  []
  (proxy [javax.swing.table.DefaultTableCellRenderer] []
    (getTableCellRendererComponent [table value isSelected hasFocus row col]
      (let [component
        (proxy-super getTableCellRendererComponent
          table value isSelected hasFocus row col)]
        (doto component
          (.setBackground
            (if (= 0 col)
              java.awt.Color/GRAY
              java.awt.Color/WHITE)))
        component))))

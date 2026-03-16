import React from "react";
 
export const MediaTrackLogo = () => {
  return (
    <div style={styles.container}>
      <svg
        width="40"
        height="40"
        viewBox="0 0 100 100"
        xmlns="http://www.w3.org/2000/svg"
      >
        <defs>
          <linearGradient id="mediaGradient" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" stopColor="#2d7bdc" />
            <stop offset="50%" stopColor="#1b4fa3" />
            <stop offset="100%" stopColor="#0b1f3f" />
          </linearGradient>
        </defs>
 
        {/* outer circle */}
        <circle cx="50" cy="50" r="45" fill="url(#mediaGradient)" />
 
        {/* swirl shape */}
        <path
          d="M35 50a20 20 0 1 1 30 15 15 15 0 1 0-30-15"
          fill="#5aa6ff"
        />
      </svg>
    </div>
  );
};
 
const styles = {
  container: {
    display: "flex",
    alignItems: "center",
    gap: "8px",
    fontSize: "20px",
    fontWeight: "600",
    color: "#ffffff",
    fontFamily: "Segoe UI, sans-serif",
  },
};
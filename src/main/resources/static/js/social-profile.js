// social-profile.js
document.addEventListener("DOMContentLoaded", function () {
  console.log("✅ social-profile.js loaded");

  const container = document.getElementById("socialProfilesContainer");
  const addBtn = document.getElementById("addProfileBtn");
  if (!container || !addBtn) return;

  // 🔹 Function to reindex names after add/remove
  const updateIndexes = () => {
    const rows = container.querySelectorAll(".social-profile-row");
    rows.forEach((row, index) => {
      const select = row.querySelector("select");
      const input = row.querySelector("input[type='url']");
      if (select) select.name = `socialLinks[${index}].platform`;
      if (input) input.name = `socialLinks[${index}].link`;
    });
  };

  // 🔹 Function to attach remove button to each row
  const attachRemoveEvent = (row) => {
    let removeBtn = row.querySelector(".removeProfileBtn");
    if (!removeBtn) {
      removeBtn = document.createElement("button");
      removeBtn.type = "button";
      removeBtn.textContent = "Remove";
      removeBtn.className =
        "removeProfileBtn px-2 py-1 bg-red-600 text-white rounded text-sm mt-1";
      row.appendChild(removeBtn);
    }

    removeBtn.addEventListener("click", () => {
      const allRows = container.querySelectorAll(".social-profile-row");
      if (allRows.length > 1) {
        row.remove();
        updateIndexes(); // reindex after removal
      } else {
        alert("At least one social profile row is required.");
      }
    });
  };

  // 🔹 Add new social profile row
  addBtn.addEventListener("click", () => {
    const firstRow = container.querySelector(".social-profile-row");
    const newRow = firstRow.cloneNode(true);

    // Clear all inputs
    newRow.querySelectorAll("select, input").forEach((el) => (el.value = ""));
    container.appendChild(newRow);

    attachRemoveEvent(newRow);
    updateIndexes(); // reindex after add
  });

  // Initialize for existing row(s)
  container
    .querySelectorAll(".social-profile-row")
    .forEach((row) => attachRemoveEvent(row));
  updateIndexes();
});

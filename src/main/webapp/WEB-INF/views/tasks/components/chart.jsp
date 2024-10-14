<div class="container mx-auto mt-4">
    <h2 class="text-lg font-bold mb-2">Task Progress</h2>

    <div class="flex items-center">
        <div class="w-full">

            <!-- Progress for completed tasks -->
            <div class="mb-1 text-base font-medium text-green-700 dark:text-green-500">Tasks Completed</div>
            <div class="w-full bg-gray-200 rounded-full h-2.5 mb-4 dark:bg-gray-700">
                <div class="bg-green-600 h-2.5 rounded-full dark:bg-green-500"
                     style="width: ${taskCount > 0 ? (taskCompletedCount * 100 / taskCount) : 0}%">
                </div>
            </div>

            <!-- Progress for in-progress tasks -->
            <div class="mb-1 text-base font-medium text-blue-700 dark:text-blue-500">In Progress Tasks</div>
            <div class="w-full bg-gray-200 rounded-full h-2.5 mb-4 dark:bg-gray-700">
                <div class="bg-blue-600 h-2.5 rounded-full dark:bg-blue-500"
                     style="width: ${taskCount > 0 ? (taskIn_progressCount * 100 / taskCount) : 0}%">
                </div>
            </div>

            <!-- Progress for overdue tasks -->
            <div class="mb-1 text-base font-medium text-red-700 dark:text-red-500">Overdue Tasks</div>
            <div class="w-full bg-gray-200 rounded-full h-2.5 mb-4 dark:bg-gray-700">
                <div class="bg-red-600 h-2.5 rounded-full dark:bg-red-500"
                     style="width: ${taskCount > 0 ? (taskOverdueCount * 100 / taskCount) : 0}%">
                </div>
            </div>

            <!-- Progress for pending tasks -->
            <div class="mb-1 text-base font-medium text-yellow-700 dark:text-yellow-500">Pending Tasks</div>
            <div class="w-full bg-gray-200 rounded-full h-2.5 mb-4 dark:bg-gray-700">
                <div class="bg-yellow-400 h-2.5 rounded-full"
                     style="width: ${taskCount > 0 ? (taskPendingCount * 100 / taskCount) : 0}%">
                </div>
            </div>

        </div>
    </div>
</div>